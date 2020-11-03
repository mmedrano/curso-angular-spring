import { Injectable } from '@angular/core';
import { formatDate } from '@angular/common';
import { Cliente  } from "./cliente";
//import { CLIENTES } from "./clientes.json";
import { of, Observable, throwError } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { map, catchError } from "rxjs/operators";
import swal from 'sweetalert2';

import { Router } from '@angular/router'

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private urlEndPoint: string = "http://localhost:8080/api/clientes";
  private httpHeaders = new HttpHeaders({'Content-type' : 'application/json'})

  constructor(private http: HttpClient, private route:Router) { }

  getClientes(): Observable<Cliente[]> {
    //return of(CLIENTES);
    return this.http.get(this.urlEndPoint).pipe(
      map( (response) => {
        let clientes = response as Cliente[];
        return clientes.map(cliente => {
          cliente.nombre = cliente.nombre.toUpperCase();
          cliente.apellido = cliente.apellido.toUpperCase();
          cliente.createAt = formatDate(cliente.createAt, 'dd-MM-yyyy', 'en-US');
          return cliente;
        });
      })
    );
  }

  create(cliente: Cliente): Observable<any> {
    return this.http.post<any>(this.urlEndPoint, cliente, { headers: this.httpHeaders}).pipe(
      catchError(e => {
        if (e.status==400) {
          return throwError(e);
        }
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  getCliente(id): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e => {
        this.route.navigate(['/clientes']);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  update(cliente: Cliente): Observable<any> {
    return this.http.put<any>(`${this.urlEndPoint}/${cliente.id}`, cliente, { headers: this.httpHeaders }).pipe(
      catchError(e => {
        if (e.status==400) {
          return throwError(e);
        }
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  delete(id: number): Observable<Cliente> {
    return this.http.delete<Cliente>(`${this.urlEndPoint}/${id}`, { headers: this.httpHeaders }).pipe(
      catchError(e => {
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
