import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ListeMembres } from './composants/liste-membres/liste-membres';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ListeMembres],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
}
