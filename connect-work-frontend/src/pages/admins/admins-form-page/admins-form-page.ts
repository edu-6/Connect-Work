import { Component } from '@angular/core';
import { Header } from "../../../shared/header/header";
import { AdminForm } from "../../../componentes/admins/admin-form/admin-form";

@Component({
  selector: 'app-admins-form-page',
  imports: [Header, AdminForm],
  templateUrl: './admins-form-page.html',
  styleUrl: './admins-form-page.css',
})
export class AdminsFormPage {


}
