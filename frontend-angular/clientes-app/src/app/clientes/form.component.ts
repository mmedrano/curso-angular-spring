import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente'
import { ClienteService } from './cliente.service'
import { Router, ActivatedRoute } from '@angular/router'
import swal from 'sweetalert2'

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {

  cliente: Cliente = new Cliente();
  titulo: string = "Crear cliente";
  errores: string[];

  constructor(private clienteService: ClienteService,
    private router: Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.cargarCliente();
  }

  public create(): void {
    this.clienteService.create(this.cliente)
      .subscribe(
        response => {
          this.router.navigate(['/clientes']);
          swal.fire('Cliente agregado', `Cliente ${response.cliente.nombre} creado con éxito.`, 'success');
        },
        err => {
          this.errores = err.error.errores as string[];
        }
    );
  }

  public cargarCliente(): void {
    this.activatedRoute.params.subscribe(params => {
      let id = params['id'];
      if (id) {
        this.clienteService.getCliente(id).subscribe(
          (cliente) => this.cliente = cliente
        )
      }
    })
  }

  public update(): void {
    this.clienteService.update(this.cliente)
      .subscribe(
        response => {
          this.router.navigate(['/clientes']);
          swal.fire('Cliente actualizado', `Cliente ${response.cliente.nombre} actualizado con éxito.`, 'success');
        },
        err => {
          this.errores = err.error.errors as string[];
        }
      )
  }

}
