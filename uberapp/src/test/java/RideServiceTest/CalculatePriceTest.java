package RideServiceTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.VehicleType;
import tim24.projekat.uberapp.repo.VehicleTypeRepository;
import tim24.projekat.uberapp.service.RideService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CalculatePriceTest
{
    @Autowired
    @InjectMocks
    private RideService rideService;
    @Mock
    private VehicleTypeRepository vehicleTypeRepository;

    @Test
    @DisplayName("Should calculate the right price")
    public void calculateValidTest()
    {
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        Mockito.when(vehicleTypeRepository.findOneByTypeName("STANDARD")).thenReturn(Optional.of(vehicleType));

        int actualPrice = rideService.calculatePrice("STANDARD", 1000);

        assertEquals(actualPrice, 200);
    }

    @Test
    @DisplayName("Should throw an ObjectNotFound exception")
    public void calculateInvalidTest()
    {
        Mockito.when(vehicleTypeRepository.findOneByTypeName("STANDARD")).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> rideService.calculatePrice("STANDARD", 1000));
    }
}
