package med.voll.api.domain.Pacientes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacientesRepository extends JpaRepository<Pacientes, Long> {
    Page<Pacientes> findAllByAtivoTrue(Pageable paginacao);


    @Query("""
        select p.ativo
        from Paciente p
        where
        p.id = :idPaciente
""")
    boolean findAtivoById(Long idPaciente);
}
