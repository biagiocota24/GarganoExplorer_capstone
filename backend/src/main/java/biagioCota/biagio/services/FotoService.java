package biagioCota.biagio.services;

import biagioCota.biagio.entities.Foto;
import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.exceptions.ResourceNotFoundException;
import biagioCota.biagio.payloads.FotoPayload;
import biagioCota.biagio.repositories.FotoRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class FotoService {

    private final FotoRepository fotoRepository;
    private final StrutturaService strutturaService;
    private final CloudinaryService cloudinaryService;

    public FotoService(FotoRepository fotoRepository,
                       StrutturaService strutturaService,
                       CloudinaryService cloudinaryService) {
        this.fotoRepository = fotoRepository;
        this.strutturaService = strutturaService;
        this.cloudinaryService = cloudinaryService;
    }

    public List<Foto> findAll() {
        return fotoRepository.findAll();
    }

    public Foto findById(Long id) {
        return fotoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Foto non trovata con id: " + id));
    }

    public Foto save(FotoPayload payload) {
        Struttura struttura = strutturaService.findById(payload.getStrutturaId());
        Foto foto = new Foto();
        foto.setStruttura(struttura);
        foto.setUrlFoto(payload.getUrlFoto());
        foto.setPosizione(payload.getPosizione());
        foto.setCaricataDaUserId(payload.getCaricataDaUserId());
        foto.setVisibile(true);
        foto.setNumeroVisite(0);
        return fotoRepository.save(foto);
    }

    @Transactional
    public String uploadForStruttura(UUID strutturaId, MultipartFile file, UUID userId) throws IOException {
        Struttura struttura = strutturaService.findById(strutturaId);
        String url = cloudinaryService.uploadImage(file);
        struttura.getRaccoltaFoto().add(url);
        strutturaService.save(struttura);
        return url;
    }

    @Transactional
    public void deleteFromStruttura(UUID strutturaId, Long fotoId, String ownerEmail) {
        Struttura struttura = strutturaService.findById(strutturaId);
        if (struttura.getBusinessOwner() == null ||
                !struttura.getBusinessOwner().getEmail().equals(ownerEmail)) {
            throw new AccessDeniedException("Non sei il proprietario di questa struttura");
        }
        Foto foto = findById(fotoId);
        fotoRepository.delete(foto);
    }

    public Foto update(Long id, FotoPayload payload) {
        Foto esistente = findById(id);
        esistente.setUrlFoto(payload.getUrlFoto());
        esistente.setPosizione(payload.getPosizione());
        return fotoRepository.save(esistente);
    }

    public void delete(Long id) {
        findById(id);
        fotoRepository.deleteById(id);
    }

    public List<Foto> findByStruttura(Struttura struttura) {
        return fotoRepository.findByStruttura(struttura);
    }

    public List<Foto> findByStrutturaId(UUID strutturaId) {
        return fotoRepository.findByStrutturaId(strutturaId);
    }

    public List<Foto> findByStrutturaIdOrdinatePerPosizione(UUID strutturaId) {
        return fotoRepository.findByStrutturaIdOrderByPosizioneAsc(strutturaId);
    }

    public List<Foto> findByVisibile(Boolean visibile) {
        return fotoRepository.findByVisibile(visibile);
    }

    public List<Foto> findVisibiliByStruttura(UUID strutturaId) {
        return fotoRepository.findByStrutturaIdAndVisibile(strutturaId, true);
    }

    public List<Foto> findByCaricataDa(UUID userId) {
        return fotoRepository.findByCaricataDaUserId(userId);
    }

    public long countByStruttura(UUID strutturaId) {
        return fotoRepository.countByStrutturaId(strutturaId);
    }
}
