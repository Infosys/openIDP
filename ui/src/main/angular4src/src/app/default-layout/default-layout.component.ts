import {Component, Input, OnInit} from '@angular/core';
import {IdprestapiService} from '../idprestapi.service';
import {CookieService} from 'ngx-cookie';
import {Router} from '@angular/router';
import {StartupService} from '../startup.service'
import {IdpdataService} from "../idpdata.service";
import { IdpService } from '../idp-service.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html',
  styleUrls:['./default-layout.scss']
})
export class DefaultLayoutComponent implements OnInit{
  public sidebarMinimized = true;
  private changes: MutationObserver;
  public element: HTMLElement = document.body;
  startupData: any;
  navItemsValue: any;
  navItems: any;
  idpBuildInfo: any = {};
  changePwdUrl: any;
  constructor(private idpRest: IdprestapiService,
              private idpService: IdpService,
              private _cookieService: CookieService,
              private router: Router,
              public idpDataService: IdpdataService,
              private startupService: StartupService) {
    this.startupData = this.startupService.getData();
  }

  setUINavBar(){
    this.navItemsValue = [
      {
        name: 'Home',
        icon: 'icon-speedometer',
        url: '/dashboard',
        attributes: {
          id: 'homeMenu'
        }
      },
      {
        name: "Dashboard",
        icon: 'icon-dashboard',
        url: "/dashboardView",
        attributes: {
          id: 'grafanaDashboardMenu'
        }

      },
      {
        name: 'Pipelines',
        icon: 'icon-puzzle',
        attributes: {
          id: 'piplelinesMenu'
        },
        children: [
          {
            name: 'View',
            url: '/previousConfig',
            class: 'mg20',
            attributes: {
              id: 'viewPipelinesMenu'
            }
          }
        ]
      },
      {
        name: 'Manage',
        icon: 'icon-puzzle',
        attributes: {
          id: 'manageMenu'
        },
        children: [
          {
            name: 'Environments',
            url: '/manageEnvironment',
            class: 'mg20',
            attributes: {
              id: 'environmentsMenu'
            }
          }
        ]
      },
      {
        name: 'Notifications',
        url: '/notificationPage',
        icon: 'icon-bell',
        attributes: {
          id: 'notificationsMenu'
        }
      },
      {
        name: 'About IDP',
        url: '/aboutView',
        icon: 'icon-info',
        attributes: {
          id: 'aboutIDPMenu'
        }
      }
    ];
    if (this.startupData.insightsFlag === true) {
        this.navItemsValue.push(
          {
            name: 'Insights',
            url: '/insights',
            icon: 'icon-speedometer',
            attributes: {
              id: 'insightsMenu'
            }
          });
    }
    if (this.idpDataService.createPipelineflag === true) {
      this.navItemsValue[2].children.push(
        {
          name: 'Create',
          url: '/createConfig',
          class: 'mg20',
          attributes: {
            id: 'createPipelineMenu'
          }
        });
    }
    if (this.idpDataService.createAppflag === true) {
      this.navItemsValue[3].children.push(
        {
          name: "Applications",
          url: '/applications',
          class: 'mg20',
          attributes: {
            id: 'viewApplicationsMenu'
          }
        });
    }
    if (this.idpDataService.showRelease === true) {
      this.navItemsValue[3].children.push(
        {
          name: 'Releases',
          url: '/releaseConfig/release',
          class: 'mg20',
          attributes: {
            id: 'releasesMenu'
          }
        });
    }

    this.navItems = this.navItemsValue;

    this.changes = new MutationObserver((mutations) => {
      this.sidebarMinimized = document.body.classList.contains('sidebar-minimized');
    });

    this.changes.observe(<Element>this.element, {
      attributes: true
    });
  }
  callforRest() {
    this.getDetails();
  }

  getDetails() {
    this.idpRest.getUserName()
        .then(response => {
        try {
            if (response) {
            const userDetails = JSON.parse(response.json().resource);
            this.idpDataService.idpUserName = userDetails.user_id;
            this.idpDataService.organization = userDetails.orgName;
            this.idpDataService.roles = userDetails.roles;
            this.idpDataService.permissions = userDetails.permissions;
            let permission = "";
            for (let i = 0; i < this.idpDataService.roles.length; i++) {
                if (this.idpDataService.role.indexOf(this.idpDataService.roles[i]) === -1) {
                this.idpDataService.role = this.idpDataService.role + this.idpDataService.roles[i] + " ";
                }
            }
            if (document.getElementById("idpUserName")) {
                document.getElementById("idpUserName").title = this.idpDataService.role;
            }
            if (this.idpDataService.role.indexOf("RELEASE_MANAGER") !== -1) {
                this.idpDataService.showRelease = true;
            }
            if (this.idpDataService.role.indexOf("IDP_ADMIN") !== -1) {
                this.idpDataService.showService = true;

            }
            for (let j = 0; j < this.idpDataService.permissions.length; j++) {
                permission = this.idpDataService.permissions[j];
                if (permission === "CREATE_APPLICATION") {
                this.idpDataService.createAppflag = true;
                }
                if (permission === "CREATE_PIPELINE") {
                this.idpDataService.createPipelineflag = true;
                }
                if (permission === "COPY_PIPELINE") {
                this.idpDataService.copyPipelineflag = true;
                }
                if (permission === "EDIT_PIPELINE") {
                this.idpDataService.editPipelineflag = true;
                }
                if (permission === "DELETE_PIEPLINE") {
                this.idpDataService.deletePipelineflag = true;
                }
                if (permission === "APPROVE_BUILD") {
                this.idpDataService.approveBuildFlag = true;
                }
                if (permission === "APPROVE_DEPLOY") {
                this.idpDataService.approveDeployFlag = true;
                }
                if (permission === "CREATE_ORGANIZATION") {
                this.idpDataService.createOrganisationflag = true;
                }

                if (permission === "CREATE_LICENSE") {
                this.idpDataService.createLicenseflag = true;
                }
              }
            }
            if (this._cookieService.get("access_token")) {//
              this.setUINavBar();
            }
        } catch (e) {
            console.log(e);
            alert("Failed in getting the user details ");
        }
        });
    return true;
  }
  logout() {
    this.idpRest.logout().then(response => {
      try {
        if (response) {
          console.log(response);
        }
      } catch (e) {
        alert("Failed to Logout");
        console.log(e);

      } finally {
        this._cookieService.removeAll();
      
          this.idpDataService.createAppflag = false;
          this.idpDataService.createPipelineflag = false;
          this.idpDataService.copyPipelineflag = false;
          this.idpDataService.editPipelineflag = false;
          this.idpDataService.deletePipelineflag = false;
          this.idpDataService.approveBuildFlag = false;
          this.idpDataService.approveDeployFlag = false;
          this.idpDataService.createOrganisationflag = false;
          this.idpDataService.createLicenseflag = false;
          this.idpDataService.showService = false;
          this.idpDataService.showRelease = false;
        this.router.navigate(["/"]);
      }
    })

  }

  ngOnInit(): void {
    this.idpRest.getIdpBuildInfo().then(response=>{
      this.idpBuildInfo = response || {};
    })
    this.callforRest();
  }

  changePasswordKC() {
    window.open(this.idpDataService.keycloakUrl + "/realms/" + this.idpDataService.keycloakRealm + "/account/password?redirect_uri="
                      + document.baseURI);
  }
}
