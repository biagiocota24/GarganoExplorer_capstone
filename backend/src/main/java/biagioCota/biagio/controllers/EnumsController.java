package biagioCota.biagio.controllers;

import biagioCota.biagio.enums.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enums")
public class EnumsController {

    @GetMapping
    public Map<String, List<Map<String, String>>> getAllEnums() {
        Map<String, List<Map<String, String>>> enums = new LinkedHashMap<>();
        enums.put("giorniSettimana", getGiorniSettimana());
        enums.put("nazionalita", getNazionalita());
        enums.put("statiAzione", getStatiAzione());
        enums.put("statiMessaggio", getStatiMessaggio());
        enums.put("tipiAttrazione", getTipiAttrazione());
        enums.put("tipiAzione", getTipiAzione());
        enums.put("tipiMerce", getTipiMerce());
        enums.put("tipiServizio", getTipiServizio());
        enums.put("tipiSpiaggia", getTipiSpiaggia());
        enums.put("tipiTrasporto", getTipiTrasporto());
        enums.put("tipologieCucina", getTipologieCucina());
        enums.put("tipologieStruttura", getTipologieStruttura());
        return enums;
    }

    @GetMapping("/giorni-settimana")
    public List<Map<String, String>> getGiorniSettimana() {
        return Arrays.stream(DayOfWeek.values())
                .map(d -> Map.of("value", d.name(), "label", d.name()))
                .toList();
    }

    @GetMapping("/nazionalita")
    public List<Map<String, String>> getNazionalita() {
        return Arrays.stream(Nazionalita.values())
                .map(n -> Map.of(
                        "value", n.name(),
                        "label", n.getNome(),
                        "bandiera", n.getBandiera()
                ))
                .toList();
    }

    @GetMapping("/stati-azione")
    public List<Map<String, String>> getStatiAzione() {
        return Arrays.stream(StatoAzione.values())
                .map(s -> Map.of(
                        "value", s.name(),
                        "label", s.getDescrizione()
                ))
                .toList();
    }

    @GetMapping("/stati-messaggio")
    public List<Map<String, String>> getStatiMessaggio() {
        return Arrays.stream(StatoMessaggio.values())
                .map(s -> Map.of(
                        "value", s.name(),
                        "label", s.getDescrizione()
                ))
                .toList();
    }

    @GetMapping("/tipi-attrazione")
    public List<Map<String, String>> getTipiAttrazione() {
        return Arrays.stream(TipoAttrazione.values())
                .map(t -> Map.of(
                        "value", t.name(),
                        "label", t.getLabel(),
                        "descrizione", t.getDescrizione(),
                        "categoria", t.getCategoria()
                ))
                .toList();
    }

    @GetMapping("/tipi-azione")
    public List<Map<String, String>> getTipiAzione() {
        return Arrays.stream(TipoAzione.values())
                .map(t -> Map.of(
                        "value", t.name(),
                        "label", t.getDescrizione()
                ))
                .toList();
    }

    @GetMapping("/tipi-merce")
    public List<Map<String, String>> getTipiMerce() {
        return Arrays.stream(TipoMerce.values())
                .map(t -> Map.of(
                        "value", t.name(),
                        "label", t.getLabel(),
                        "descrizione", t.getDescrizione(),
                        "categoria", t.getCategoria()
                ))
                .toList();
    }

    @GetMapping("/tipi-servizio")
    public List<Map<String, String>> getTipiServizio() {
        return Arrays.stream(TipoServizio.values())
                .map(t -> Map.of(
                        "value", t.name(),
                        "label", t.getLabel(),
                        "descrizione", t.getDescrizione(),
                        "categoria", t.getCategoria()
                ))
                .toList();
    }

    @GetMapping("/tipi-spiaggia")
    public List<Map<String, String>> getTipiSpiaggia() {
        return Arrays.stream(TipoSpiaggia.values())
                .map(t -> Map.of("value", t.name(), "label", t.name()))
                .toList();
    }

    @GetMapping("/tipi-trasporto")
    public List<Map<String, String>> getTipiTrasporto() {
        return Arrays.stream(TipoTrasporto.values())
                .map(t -> Map.of("value", t.name(), "label", t.name()))
                .toList();
    }

    @GetMapping("/tipologie-cucina")
    public List<Map<String, String>> getTipologieCucina() {
        return Arrays.stream(TipologiaCucina.values())
                .map(t -> Map.of(
                        "value", t.name(),
                        "label", t.getLabel(),
                        "descrizione", t.getDescrizione(),
                        "categoria", t.getCategoria()
                ))
                .toList();
    }

    @GetMapping("/tipologie-struttura")
    public List<Map<String, String>> getTipologieStruttura() {
        return Arrays.stream(TipologiaStruttura.values())
                .map(t -> Map.of(
                        "value", t.name(),
                        "label", t.getLabel(),
                        "descrizione", t.getDescrizione(),
                        "url",t.getUrl()
                ))
                .toList();
    }
}
