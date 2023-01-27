package tim24.projekat.uberapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.PanicDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.UserRef;
import tim24.projekat.uberapp.model.Panic;
import tim24.projekat.uberapp.repo.PanicRepository;

@Service
public class PanicService
{
	@Autowired
	private PanicRepository panicRepo;
	@Autowired
	private DateUtils du;
	public DTOList<PanicDTO> getPanic()
	{
		DTOList<PanicDTO> panicList = new DTOList<PanicDTO>();
        List<Panic> panics = panicRepo.findAll();
        for(Panic p : panics) {
        	panicList.add(new PanicDTO(p.getId(), new UserRef(p.getUser()), new RideDTO(p.getRide()), du.formatDate(p.getTime()), p.getReason()));
        }
        return panicList;
	}
	
	
}
