package domain;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.instancio.Instancio;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class CandidateServiceTest {

    @Inject
    CandidateService service;

    @InjectMock
    CandidateRepository repository;

    @Test
    void save() {
        var candidate = Instancio.create(Candidate.class);

        service.save(candidate);

        verify(repository).save(candidate);
        verifyNoMoreInteractions(repository);
    } 
    
    @Test 
    void findAll() {
        var candidates = Instancio.stream(Candidate.class).limit(10).toList();

        when(repository.findAll()).thenReturn(candidates);

        var result = service.findAll();

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
        assertEquals(candidates, result);
    }

    @Test
    void findById_whenCandidateIsFound_thenReturnACandidate() {
        var candidate = Instancio.create(Candidate.class);

        when(repository.findById(candidate.id())).thenReturn(Optional.of(candidate));

        var result = service.findById(candidate.id());

        verify(repository).findById(candidate.id());
        verifyNoMoreInteractions(repository);
        assertEquals(candidate, result);
    }

    @Test
    void findById_whenCandidateIsNotFound_throwsException() {
        var candidate = Instancio.create(Candidate.class);

        when(repository.findById(candidate.id())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.findById(candidate.id()));

        verify(repository).findById(candidate.id());
        verifyNoMoreInteractions(repository);
    }
}
