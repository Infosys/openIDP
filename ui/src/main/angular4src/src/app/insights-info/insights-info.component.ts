import { Component, OnInit } from "@angular/core";
import { Router, NavigationEnd } from "@angular/router";

@Component({
  // tslint:disable-next-line
  template: "<router-outlet></router-outlet>",
})
export class InsightsInfoComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit() {
    // this.router.events.subscribe((evt) => {
    //   if (!(evt instanceof NavigationEnd)) {
    //     return;
    //   }
    //   window.scrollTo(0, 0);
    // });
  }
}
