package tim24.projekat.uberapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tim24.projekat.uberapp.model.Note;

public interface NoteRepository  extends JpaRepository<Note, Long>
{

}
