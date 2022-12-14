package tim24.projekat.uberapp.service;

import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.UserRequestDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;

@Service
public class DriverService {

	public UserResponseDTO createDriver(UserRequestDTO newDriver) {
		return new UserResponseDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
	}

	public DTOList<UserResponseDTO> GetAllDrivers(int page, int size) {
		DTOList<UserResponseDTO> list = new DTOList<UserResponseDTO>();
		UserResponseDTO driver1 = new UserResponseDTO(1L,"Stefan","Bogdanovic","profilePicture.jpg","+3810641234567","mail@email.com","Bulevar Oslobodjenja 169");
		list.add(driver1);
		UserResponseDTO driver2 = new UserResponseDTO(1L,"Pero","Perovic","profilePicture.jpg","+3810641235667","mail@email.com","Bulevar Evrope 42");
		list.add(driver2);
		return list;
	}

}
