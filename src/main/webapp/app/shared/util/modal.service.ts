import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
@Injectable({ providedIn: 'root' })
export class ModalService {
    constructor(private _snackBar: MatSnackBar, public dialog: MatDialog) {}

    actionMessage(message: string, action: string) {
        this._snackBar.open(message, action, {
            duration: 3000
        });
    }
    message(message: string) {
        this._snackBar.open(message, '', {
            duration: 3000,
            horizontalPosition: 'end'
        });
    }

    confirmDialog(type, success): void {
        let title, text: String;
        switch (type) {
            case 'delete': {
                title = 'Eliminar';
                text = '¿Está seguro que desea eliminarlo?';
                break;
            }
            case 'new': {
                title = 'Registrar';
                text = '¿Está seguro que desea realizar el registro?';
                break;
            }
            case 'update': {
                title = 'Editar';
                text = '¿Está seguro que desea editarlo?';
                break;
            }
        }
        const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            width: 'calculate(100% - 60px)',
            data: { confirmed: true, title, text }
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                success();
            }
        });
    }
    confirmCustomDialog(title, text, success): void {
        const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            width: 'calculate(100% - 60px)',
            data: { confirmed: true, title, text }
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                success();
            }
        });
    }
}
