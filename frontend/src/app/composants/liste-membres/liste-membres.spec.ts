import { ComponentFixture, TestBed } from '@angular/core/testing';
import { vi } from 'vitest';
import { of } from 'rxjs';
import { MembreService } from '../../services/membre.service';
import { Membre } from '../../model/membre.model';
import { ListeMembres } from './liste-membres';

describe('ListeMembres', () => {

  let composant: ListeMembres;
  let fixture: ComponentFixture<ListeMembres>;
  let membreServiceSimule: {
    recupererTousLesMembres: ReturnType<typeof vi.fn>,
    enregistrerLeVoteDuMembre: ReturnType<typeof vi.fn>
  };

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
      aVote: true
    }
  ];

  beforeEach(async () => {
    membreServiceSimule = {
      recupererTousLesMembres: vi.fn().mockReturnValue(of(listeMembresSimulee)),
      enregistrerLeVoteDuMembre: vi.fn()
    };

    await TestBed.configureTestingModule({
      imports: [ListeMembres],
      providers: [
        { provide: MembreService, useValue: membreServiceSimule }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ListeMembres);
    composant = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('devrait être créé', () => {
    expect(composant).toBeTruthy();
  });

  it('devrait charger la liste des membres au démarrage', () => {
    expect(membreServiceSimule.recupererTousLesMembres).toHaveBeenCalledTimes(1);
    expect(composant.listeMembres.length).toBe(2);
  });

  it('devrait afficher le bouton Voter si le membre na pas encore voté', () => {
    const elementHtml: HTMLElement = fixture.nativeElement;
    const boutons = elementHtml.querySelectorAll('button');
    expect(boutons.length).toBe(1);
    expect(boutons[0].textContent).toContain('Voter');
  });

  it('devrait afficher A voté si le membre a déjà voté', () => {
    const elementHtml: HTMLElement = fixture.nativeElement;
    const texteAVote = elementHtml.querySelectorAll('span');
    expect(texteAVote.length).toBe(1);
    expect(texteAVote[0].textContent).toContain('A voté');
  });

  it('devrait appeler le service et mettre à jour le membre dans la liste', () => {
    const membreMisAJour: Membre = {
      identifiant: 1,
      nom: 'Dupont',
      prenom: 'Jean',
      dateDeNaissance: '1985-03-15',
      aVote: true
    };

    membreServiceSimule.enregistrerLeVoteDuMembre.mockReturnValue(of(membreMisAJour));

    composant.voterPourUnMembre(1);

    expect(membreServiceSimule.enregistrerLeVoteDuMembre).toHaveBeenCalledWith(1);
    expect(composant.listeMembres[0].aVote).toBe(true);
  });
});
