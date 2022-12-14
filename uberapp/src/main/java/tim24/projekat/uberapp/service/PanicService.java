package tim24.projekat.uberapp.service;

import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.PanicDTO;
import tim24.projekat.uberapp.repo.PanicRepository;

@Service
public class PanicService {

	private PanicRepository panicRepo;

	public DTOList<PanicDTO> getPanic() {
		DTOList<PanicDTO> panicList = new DTOList<PanicDTO>();
        PanicDTO panicDto = new PanicDTO();
        panicList.add(panicDto);
        return panicList;
	}
	
	
}
