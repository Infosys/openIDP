import { Routes, RouterModule } from "@angular/router";
import { CodeInfoComponent } from "./code-info.component";


const CODE_INFO_ROUTER: Routes = [
    {
        path: "",
        component: CodeInfoComponent
    }
];

export const codeInfoRouter = RouterModule.forChild(CODE_INFO_ROUTER );
