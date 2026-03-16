import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MembreService } from './membre.service';
import { Membre } from '../model/membre.model';

describe('MembreService', () => {

  let membreService: MembreService;
  let controleurHttp: HttpTestingController;

  const urlDuBackend = 'http://localhost:8080/membres';

  const listeMembresSimulee: Membre[] = [
    {
      identifiant: 1,
      nom: 'Dupont',
      prenom: 'Jean',
      dateDeNaissance: '1985-03-15',
      aVote: false
    },
    {
      identifiant: 2,
      nom: 'Martin',
      prenom: 'Sophie',
      dateDeNaissance: '1990-07-22',
      aVote: false
    }
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MembreService]
    });

    membreService = TestBed.inject(MembreService);
    controleurHttp = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    controleurHttp.verify();
  });

  // -------------------------------------------------------
  // Tests sur recupererTousLesMembres()
  // -------------------------------------------------------

  it('devrait être créé', () => {
    expect(membreService).toBeTruthy();
  });

  it('devrait récupérer tous les membres via un GET', () => {
    membreService.recupererTousLesMembres().subscribe({
      next: (membres) => {
        expect(membres.length).toBe(2);
        expect(membres[0].nom).toBe('Dupont');
        expect(membres[1].nom).toBe('Martin');
      }
    });

    const requeteSimulee = controleurHttp.expectOne(urlDuBackend);
    expect(requeteSimulee.request.method).toBe('GET');
    requeteSimulee.flush(listeMembresSimulee);
  });

  it('devrait retourner une liste vide si aucun membre en base', () => {
    membreService.recupererTousLesMembres().subscribe({
      next: (membres) => {
        expect(membres.length).toBe(0);
      }
    });

    const requeteSimulee = controleurHttp.expectOne(urlDuBackend);
    requeteSimulee.flush([]);
  });

  // -------------------------------------------------------
  // Tests sur enregistrerLeVoteDuMembre()
  // -------------------------------------------------------

  it('devrait enregistrer le vote via un PUT et retourner le membre mis à jour', () => {
    const membreMisAJour: Membre = {
      identifiant: 1,
      nom: 'Dupont',
      prenom: 'Jean',
      dateDeNaissance: '1985-03-15',
      aVote: true
    };

    membreService.enregistrerLeVoteDuMembre(1).subscribe({
      next: (membre) => {
        expect(membre.aVote).toBeTruthy();
        expect(membre.identifiant).toBe(1);
      }
    });

    const requeteSimulee = controleurHttp.expectOne(`${urlDuBackend}/1/voter`);
    expect(requeteSimulee.request.method).toBe('PUT');
    requeteSimulee.flush(membreMisAJour);
  });
});
