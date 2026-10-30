import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Producto } from '../interfaces/producto.interface';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private http = inject(HttpClient);
  //usamos la ruta del backend
  private  apiUrl = '/api/v1/productos';

  //listar ruta publica
  getProductos():Observable<Producto[]>{
    return this.http.get<Producto[]>(this.apiUrl);
  }
  //obtener por ID
  getProductoById(id:number) : Observable<Producto>{

    return this.http.get<Producto>(`${this.apiUrl}/${id}`).pipe(
      tap(productos =>{
        console.log('Productos recibidos', productos);
      })
    );
  }

  //metodos para el admin
  crearProducto(formData: FormData): Observable<Producto>{
    console.log("formData recibido create");
    formData.forEach((value,key)=>{
      console.log(`${key}:`,value);
    })
    return this.http.post<Producto>(this.apiUrl,formData);
  }

  actualizarProducto(id:number,formData: FormData): Observable<Producto>{
    console.log("formData recibido update");
    formData.forEach((value,key)=>{
      console.log(`${key}:`,value);
    })
    return this.http.put<Producto>(`${this.apiUrl}/${id}`,formData);
  }

  eliminarProducto(id:number):Observable<void>{
    console.log("ID del producto a eliminar",id);
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }


}
