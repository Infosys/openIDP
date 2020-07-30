import { Component, OnInit } from "@angular/core";
import { IdprestapiService } from "../idprestapi.service";

@Component({
  selector: "app-application-list",
  templateUrl: "./application-list.component.html",
  styleUrls: ["./application-list.component.scss"],
})
export class ApplicationListComponent implements OnInit {
  applicationList: Array<any> = [];
  currentPage: any;
  appNameQuery: string;
  constructor(private idpRest: IdprestapiService) {}

  ngOnInit() {
    this.getApplicationsList();
  }

  getApplicationsList() {
    this.idpRest.getExistingApps().then((response) => {
      try {
        if (response) {
          let appDetails = JSON.parse(response.json().resource);
          this.applicationList = appDetails.applications;
        }
      } catch (e) {
        alert("Failed while getting applications names");
      }
    });
  }
}
