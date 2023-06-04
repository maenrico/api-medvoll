package med.voll.api.domain.repository;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable pageable);

    @Query("""
            SELECT m FROM Medico m
            WHERE m.ativo = 1
            AND m.especialidade = :especialidade
            AND m.id NOT IN(
                SELECT c.medico.id from Consulta c
                WHERE c.data = :data
            )
            ORDER BY rand()
            LIMIT 1
            """)
    Medico escolherMedicoAleatorioLivre(Especialidade especialidade, LocalDateTime data);

    @Query("SELECT m.ativo FROM Medico m WHERE m.id = :id")
    Boolean findAtivoById(Long id);
}