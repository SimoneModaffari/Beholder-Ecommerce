import { Component, ElementRef, QueryList, ViewChildren } from '@angular/core';

@Component({
  selector: 'app-add-tocart',
  templateUrl: './add-tocart.component.html',
  styleUrl: './add-tocart.component.css'
})
export class AddTocartComponent {
  @ViewChildren('button') buttons!: QueryList<ElementRef>;

  ngAfterViewInit() {
    this.buttons.forEach(button => {
      button.nativeElement.addEventListener('click', (e: Event) => this.handleButtonClick(button.nativeElement, e));
    });
  }

  handleButtonClick(button: HTMLElement, event: Event) {
    if (!button.classList.contains('loading')) {
      button.classList.add('loading');

      setTimeout(() => button.classList.remove('loading'), 3700);
    }
    event.preventDefault();
  }
}

