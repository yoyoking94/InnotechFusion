import { ChangeDetectorRef, Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Membre } from "../../model/membre.model";
import { MembreService } from "../../services/membre.service";

@Component({
  selector: 'app-liste-membres',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './liste-membres.html',
  styleUrl: './liste-membres.css',
})
export class ListeMembres implements OnInit {
  listeMembres: Membre[] = [];

  constructor(
    private membreService: MembreService,
    private detecteurDeChangements: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.chargerLaListeDesMembres();
  }

  chargerLaListeDesMembres(): void {
    this.membreService.recupererTousLesMembres().subscribe({
      next: (membres) => {
        this.listeMembres = membres;
        this.detecteurDeChangements.detectChanges(); // ← force Angular à rafraîchir la vue
      },
      error: (erreur) => {
        console.error('Erreur lors du chargement des membres :', erreur);
      }
    });
  }

  voterPourUnMembre(identifiant: number): void {
    this.membreService.enregistrerLeVoteDuMembre(identifiant).subscribe({
      next: (membreMisAJour) => {
        const index = this.listeMembres.findIndex(
          membre => membre.identifiant === membreMisAJour.identifiant
        );
        if (index !== -1) {
          this.listeMembres[index] = membreMisAJour;
          this.detecteurDeChangements.detectChanges(); // ← idem après un vote
        }
      },
      error: (erreur) => {
        console.error('Erreur lors de l\'enregistrement du vote :', erreur);
      }
    });
  }
}
