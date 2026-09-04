# BACKEND AUDIT REPORT — GarganoExplorer
> Analisi completa del progetto Spring Boot

---

## 1. PROBLEMI TROVATI

### 🔴 CRITICI

| # | Problema | Causa | Impatto | Soluzione applicata |
|---|---|---|---|---|
| 1 | **Password hash esposta nel JSON** | `User.password` non aveva `@JsonIgnore`. `LoginResponse` passava l'entity `User` diretta. | Ogni risposta di login/register mandava il bcrypt hash al client — vulnerabilità di sicurezza grave. | Aggiunto `@JsonIgnore` su `User.password`. `LoginResponse` ora usa `UserResponse` DTO. |
| 2 | **Errore di compilazione in AuthController** | `UserPayloadResponse.fromEntity()` non esiste (solo `fromVisitorEntity`, `fromAdminEntity`, ecc.). | Il progetto **non compilava**. | `AuthController` riscritto per usare `UserResponse.fromEntity()`. |
| 3 | **`RuntimeException` usata per 404** | Tutti i service lanciavano `RuntimeException("non trovato")`. | L'`ErrorHandler` non la intercettava → risposta 500 invece di 404. | Creata `ResourceNotFoundException` → gestita con 404. |
| 4 | **`DuplicateEmailException` restituiva HTTP 208** | `@ResponseStatus(HttpStatus.ALREADY_REPORTED)` nell'ErrorHandler. | 208 significa "multi-status WebDAV" — semantica completamente errata. | Cambiato a 409 CONFLICT. |
| 5 | **`autoreId` nel body di `ValutazionePayload`** | Il client passava l'UUID dell'autore nel body della richiesta. | Un utente poteva creare recensioni a nome di qualsiasi altro utente. | Nuovo `ValutazioneCreatePayload` senza `autoreId`. L'autore viene estratto dal JWT. |
| 6 | **`autoreId` nel body di `PreferitoPayload`** | Stesso pattern di sopra. | Stesso problema di sicurezza. | `PreferitoAddPayload` senza `autoreId`. L'autore viene estratto dal JWT. |
| 7 | **`Negozio.tipoMerce` è `List<TipoMerce>` su `@Column`** | JPA non sa come persistere una lista di enum su una colonna singola. | `@Column` su un tipo non mappabile → errore a runtime. | ✅ **Corretto** — aggiunto `@ElementCollection @Enumerated(EnumType.STRING)`, tabella join `negozio_tipi_merce`. |
| 8 | **`Ristorante.fasciaPrezzoMedio` usa `@Column` su `@Embeddable`** | `RangePrezzo` è un `@Embeddable` ma era annotato con `@Column`. | I campi `prezzo_minimo`/`prezzo_massimo` non venivano mappati. | ✅ **Corretto** — sostituito con `@Embedded`. Aggiunti `@Getter @Setter` a `RangePrezzo`. |
| 9 | **`Visitor.messaggiBlog` mapping errato** | `mappedBy = "autore"` ma `Messaggio.autore` era di tipo `User`, non `Visitor`. | La collection non si popolava correttamente con Hibernate SINGLE_TABLE. | ✅ **Corretto** — `Messaggio.autore` cambiato da `User` a `Visitor`. |
| 10 | **Zero-width space in `Foto.dataCa​ricamento`** | Carattere invisibile nel nome del campo. | Errore silente nel mapping JPA. | ✅ **Corretto** — rinominato in `dataCaricamento`. |

---

### 🟡 IMPORTANTI

| # | Problema | Impatto |
|---|---|---|
| 1 | **Nessun `@Transactional`** sui metodi save/update dei service. | Operazioni multi-step non atomiche, rischio di dati inconsistenti. ✅ **Risolto**: aggiunti `@Transactional` su tutti i service. |
| 2 | **Nessuna paginazione** su `findAll()`. | Con 10.000 strutture `GET /strutture` restituisce tutto in memoria → OOM. ✅ **Risolto**: `StrutturaService` usa `Page<Struttura>`. |
| 3 | **`UserPayloadResponse` mista request/response** | Annotazioni `@NotBlank` su campi response, `@NotBlank private UUID id` su UUID. ✅ **Risolto**: creata `UserResponse` solo per le risposte. |
| 4 | **Nessun `@PreAuthorize`** su endpoint sensibili. | Chiunque autenticato poteva fare qualsiasi cosa. ✅ **Parzialmente risolto**: `@PreAuthorize` su `ValutazioneController` e `PreferitoController`. |
| 5 | **`UserController` completamente vuoto**. | Nessun endpoint utente implementato. ✅ **Risolto**: aggiunto `GET /users/me` e `PATCH /users/me`. |
| 6 | **`RuntimeException` catch-all** non gestita. | Frontend riceveva formati errore inconsistenti. ✅ **Risolto**: handler `RuntimeException` nell'`ErrorHandler`. |
| 7 | **`@Data` su entità JPA** (`Messaggio`, `LogAzione`). | `equals/hashCode` su tutte le collection → `StackOverflowError` con relazioni bidirezionali. ✅ **Risolto**: sostituito con `@Getter @Setter`. |
| 8 | **Enum senza `@Enumerated(EnumType.STRING)`** su 5 subclass Struttura. | JPA salva l'ordinale intero → aggiungere un valore all'enum rompe tutti i dati. ✅ **Risolto**: aggiunto `@Enumerated(EnumType.STRING)` su `AttrazionePrincipale`, `Servizio`, `StabilimentoBalneare`, `Trasporto`, `Ristorante`. |
| 9 | **Typo `dataAssunsione`** in `Admin`, `AdminPayload`, `AdminRepository`, `AdminService`. | Confusione e campo con nome sbagliato nel DB. ✅ **Risolto**: rinominato `dataAssunzione` ovunque. |
| 10 | **CORS** (sessione precedente). | ✅ **Già risolto**. |

---

### 🟢 MINORI

| # | Problema |
|---|---|
| 1 | **Typo `biografy`** — dovrebbe essere `biography` o `biografia`. Pervasivo in tutta la codebase. |
| 2 | **Typo `dataAssunsione`** in `Admin` — dovrebbe essere `dataAssunzione`. |
| 3 | **`@Data` su entity JPA** (`Messaggio`, `LogAzione`) — Lombok `@Data` genera `equals/hashCode` su tutti i campi, rompendo Hibernate. Usare `@Getter @Setter` come negli altri entity. |
| 4 | **Inconsistenza ID**: `Struttura`, `User`, `Valutazione` usano `UUID`. `Messaggio`, `PostCommunity`, `Foto` usano `Long`. |
| 5 | **`DataSeeder` usa "pass123"** che probabilmente non supera `@ValidPassword`. |
| 6 | **`AuthService` lanciava `DuplicateEmailException("Utente non trovato")`** — eccezione semanticamente sbagliata per un utente non trovato. Corretto con `ResourceNotFoundException`. |
| 7 | **`AuthController` restituiva 202 ACCEPTED per il login** — il login non è asincrono. Corretto a 200 OK. |
| 8 | **Nessun indice DB esplicito** su `email`, `struttura_id`, `autore_id`. JPA aggiunge solo FK ma non indici su colonne di ricerca frequente. Aggiungere `@Index` nelle `@Table`. |

---

## 2. ENDPOINT — STATO ATTUALE

### ✅ Esistenti e funzionanti
| Metodo | Path | Accesso |
|---|---|---|
| POST | `/auth/login` | Public |
| POST | `/auth/register/visitor` | Public |
| POST | `/auth/register/owner` | Public |
| POST | `/auth/register/admin` | Public |
| GET | `/enums` | Public |
| GET | `/enums/{tipo}` | Public |

### ✅ Nuovi implementati in questa sessione
| Metodo | Path | Accesso | Note |
|---|---|---|---|
| GET | `/users/me` | Autenticato | Profilo utente corrente |
| PATCH | `/users/me` | Autenticato | Aggiorna profilo |
| GET | `/strutture` | Public | Paginato, filtri tipologia+città |
| GET | `/strutture/{id}` | Public | Dettaglio singola |
| GET | `/strutture/{id}/valutazioni` | Public | Recensioni per struttura |
| POST | `/valutazioni` | VISITOR | Crea recensione (autore da JWT) |
| PATCH | `/valutazioni/{id}` | VISITOR | Modifica propria recensione |
| DELETE | `/valutazioni/{id}` | VISITOR / ADMIN | Elimina recensione |
| GET | `/valutazioni/me` | VISITOR | Le mie recensioni |
| GET | `/preferiti/me` | VISITOR | I miei preferiti |
| POST | `/preferiti/{strutturaId}` | VISITOR | Aggiungi ai preferiti |
| DELETE | `/preferiti/{id}` | VISITOR | Rimuovi dai preferiti |
| GET | `/citta` | Public | Lista tutte le città |
| GET | `/citta/{id}` | Public | Singola città |

### ❌ Ancora mancanti (Priority 2-3)
| Priorità | Metodo | Path | Complessità |
|---|---|---|---|
| P2 | GET | `/admin/users` | Media — paginazione + filtri |
| P2 | PATCH | `/admin/users/{id}/ban` | Semplice |
| P2 | DELETE | `/admin/users/{id}` | Semplice |
| P2 | POST | `/strutture/{id}/foto` | Media — upload Cloudinary |
| P2 | DELETE | `/strutture/{id}/foto/{fotoId}` | Semplice |
| P2 | GET | `/business/strutture` | Semplice — by businessOwner from JWT |
| P3 | GET | `/admin/analytics` | Difficile |
| P3 | GET | `/admin/logs` | Media |
| P3 | POST | `/search` | Media — full-text search |

---

## 3. REFACTOR CONSIGLIATI

1. **Creare un `BaseService<T, ID>`** con `findById`, `save`, `delete` per eliminare la duplicazione tra i service.

2. **`UserPayloadResponse` da deprecare** — ora sostituita da `UserResponse`. Il file può essere rimosso dopo aver verificato che non sia referenziato altrove.

3. **Mapper dedicato** (es. con MapStruct) invece dei metodi `static fromEntity()` nei DTO: più manutenibile quando le entità cambiano.

4. **`Struttura` hierarchy responses**: creare `HotelResponse`, `RistoranteResponse`, ecc. che estendono `StrutturaResponse` per esporre i campi specifici (stelle hotel, tipologiaCucina ristorante, ecc.).

5. **`@ControllerAdvice` unificato** già fatto — ma aggiungere logging strutturato (es. SLF4J) invece di `System.out.println`.

---

## 4. BEST PRACTICES VIOLATE

| Categoria | Problema |
|---|---|
| **REST** | `DELETE /preferiti/{id}` dovrebbe restituire 204 No Content — ✅ già corretto |
| **REST** | `POST /auth/login` non dovrebbe usare i path `/auth/register/*` per la registrazione (convenzionalmente `/users` per POST create) — accettabile per capstone |
| **Security** | Password minima 8 char con `@ValidPassword` custom — ✅ già implementata, ma il DataSeeder usa "pass123" |
| **JPA** | `FetchType.EAGER` su `Valutazione.autore` e `Valutazione.struttura` — rischio N+1 in liste |
| **JPA** | `@Data` su entity con `@ManyToMany` e collections può causare `StackOverflowError` in `hashCode` |
| **Spring** | `@Autowired` nel `DataSeeder` — meglio injection via costruttore |

---

## 5. PRIORITÀ DI IMPLEMENTAZIONE

### Priority 1 — Completato ✅
- Auth flow (login/register) — funzionante
- Password hash sicura — ✅
- Password non esposta nel JSON — ✅ (fix applicato)
- Profilo utente (`/users/me`) — ✅
- Lista strutture paginata — ✅
- Recensioni (CRUD sicuro) — ✅
- Preferiti — ✅
- Error handling coerente — ✅
- CORS — ✅

### Priority 2 — Da fare
- Admin: ban/delete utenti, approvazione strutture
- BusinessOwner: gestione proprie strutture, upload foto
- Refresh token
- Paginazione su altri endpoint (valutazioni, preferiti)

### Priority 3 — Nice to have
- Analytics admin (`/admin/analytics`)
- Ricerca full-text (`/search`)
- Rate limiting (es. Bucket4j)
- Caching (es. Spring Cache su `/citta`, `/enums`)
- Soft delete per utenti (campo `deletedAt` invece di cancellazione fisica)
