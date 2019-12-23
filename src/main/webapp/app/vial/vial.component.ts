import {Component, OnInit} from '@angular/core';
import {EMPTY, from, of, throwError} from 'rxjs';
import {catchError, switchMap} from 'rxjs/operators';

@Component({
  selector: 'jhi-vial',
  templateUrl: './vial.component.html',
  styleUrls: ['./vial.component.scss']
})
export class VialComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
    this.test();
  }

  test() {
    const observable = from([0, 2, 4, 3, 8, 10]).pipe(
      switchMap(value => {
        if (value === 3) {
          return throwError('AIE').pipe(catchError(err => EMPTY));
        } else {
          return of(value * 2).pipe(catchError(err => EMPTY));
        }
      })
    );

    observable.subscribe(
      (value) => console.log("onNext " + value), (error) => console.log("Error: " + error.message), () => console.log("Completed!")
    );
  }

}
