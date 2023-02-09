package RideServiceTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import tim24.projekat.uberapp.DTO.SuccessDTO;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.*;
import tim24.projekat.uberapp.repo.FavoriteRideRepository;
import tim24.projekat.uberapp.service.RideService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class DeleteFavoriteRideTest
{
    @Autowired
    @InjectMocks
    private RideService rideService;

    @Mock
    private FavoriteRideRepository favoriteRideRepository;

    @Test
    @DisplayName("Should retrieve an UnregisteredResponseDTO")
    public void deleteFavoriteRideValid()
    {
        FavoriteRide favoriteRide = new FavoriteRide();

        Mockito.when(favoriteRideRepository.findById(1L)).thenReturn(Optional.of(favoriteRide));

        SuccessDTO successDTO = rideService.deleteFavoriteRide(1L);

        assertEquals(successDTO.getMessage(), "Successful deletion of favorite location!");
    }

    @Test
    @DisplayName("Should throw an IObjectNotFoundException exception")
    public void deleteFavoriteRideInvalid()
    {
        Mockito.when(favoriteRideRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> rideService.deleteFavoriteRide(1L));
    }
}
