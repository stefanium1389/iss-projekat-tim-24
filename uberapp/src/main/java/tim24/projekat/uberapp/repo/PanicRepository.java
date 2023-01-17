package tim24.projekat.uberapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tim24.projekat.uberapp.model.Panic;

public interface PanicRepository extends JpaRepository<Panic, Long>
{
    Panic save(Panic panic);
}
