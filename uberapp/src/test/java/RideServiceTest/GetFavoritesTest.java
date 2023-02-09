package RideServiceTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import tim24.projekat.uberapp.DTO.FavoriteRideResponseDTO;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.FavoriteRide;
import tim24.projekat.uberapp.model.Role;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.repo.FavoriteRideRepository;
import tim24.projekat.uberapp.repo.UserRepository;
import tim24.projekat.uberapp.service.RideService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GetFavoritesTest
{
    @Autowired
    @InjectMocks
    private RideService rideService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FavoriteRideRepository favoriteRideRepository;

    @Test
    @DisplayName("Should retrieve an UnregisteredResponseDTO")
    public void getFavoritesValid()
    {
        User user = new User(1L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        List<FavoriteRide> mockList = new ArrayList<>();

        Mockito.when(userRepository.findUserByEmail("a")).thenReturn(Optional.of(user));
        Mockito.when(favoriteRideRepository.findAllFavoriteRideByPassengerId(1L)).thenReturn(mockList);

        List<FavoriteRideResponseDTO> actualList = rideService.getFavorites("a");

        assertEquals(actualList.size(), 0);
    }

    @Test
    @DisplayName("Should throw an ObjectNotFoundException exception")
    public void getFavoritesInvalid()
    {
        Mockito.when(userRepository.findUserByEmail("a")).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> rideService.getFavorites("a"));
    }
}
