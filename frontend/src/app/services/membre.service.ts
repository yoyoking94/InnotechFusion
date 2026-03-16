import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Membre } from '../model/membre.model';

@Injectable({
  providedIn: 'root',
})
export class MembreService {
  private urlDuBackend = 'http://localhost:8080/membres';

  constructor(private clientHttp: HttpClient) { }

  recupererTousLesMembres(): Observable<Membre[]> {
    return this.clientHttp.get<Membre[]>(this.urlDuBackend);
  }

  // ✅ CORRECT — on retourne bien un seul Membre
  enregistrerLeVoteDuMembre(identifiant: number): Observable<Membre> {
    return this.clientHttp.put<Membre>(`${this.urlDuBackend}/${identifiant}/voter`, {});
  }

}
