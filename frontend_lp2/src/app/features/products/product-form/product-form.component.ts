
import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ProductoService } from '../../../core/services/producto.service';
import { AlertService } from '../../../core/services/alert.service';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.css'
})
export class ProductFormComponent implements OnInit {

  private fb = inject(FormBuilder);
  private productoService = inject(ProductoService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private alertService = inject(AlertService);

  form: FormGroup;
  idProducto: number | null = null;
  fotoSeleccionada: File | null = null;

  constructor() {
    this.form = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      descripcion: [''],
      precio: [0, [Validators.required, Validators.min(0.1)]],
      stock: [0, [Validators.required, Validators.min(0.1)]]
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.idProducto = idParam ? Number(idParam) : null;

    console.log('ID del producto:', this.idProducto);

    if (this.idProducto !== null && !Number.isNaN(this.idProducto)) {
      this.productoService.getProductoById(this.idProducto).subscribe({
        next: (producto) => {
          console.log('Producto recibido:', producto);

          this.form.patchValue({
            nombre: producto.nombre,
            descripcion: producto.descripcion,
            precio: producto.precio,
            stock: producto.stock
          });
        },
        error: (err) => {
          console.error(err);
          this.alertService.error('Error al cargar el producto');
        }
      });
    }
  }

  // Seleccionar imagen
  onFileSeleccionada(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
      this.fotoSeleccionada = input.files[0];
    }
  }

  // Guardar o actualizar producto
  guardar(): void {
    if (this.form.invalid) return;

    const formData = new FormData();

    formData.append('nombre', this.form.value.nombre);
    formData.append('descripcion', this.form.value.descripcion);
    formData.append('precio', String(this.form.value.precio));
    formData.append('stock', String(this.form.value.stock));

    if (this.fotoSeleccionada) {
      formData.append('file', this.fotoSeleccionada);
    }

    const request = this.idProducto
      ? this.productoService.actualizarProducto(this.idProducto, formData)
      : this.productoService.crearProducto(formData);

    request.subscribe({
      next: () => {
        this.alertService.success(
          this.idProducto ? 'Producto actualizado' : 'Producto creado'
        );

        this.router.navigate(['/productos']);
      },
      error: (err) => {
        console.error(err);
        this.alertService.error('Ocurrió un error al guardar el producto');
      }
    });
  }
}
