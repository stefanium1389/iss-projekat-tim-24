package tim24.projekat.uberapp.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tim24.projekat.uberapp.model.DriverReport;
import tim24.projekat.uberapp.model.DriverUpdateDetails;
import tim24.projekat.uberapp.model.Role;
import tim24.projekat.uberapp.model.User;


public interface DriverReportRepo extends JpaRepository<DriverReport, Long> {

	

}
