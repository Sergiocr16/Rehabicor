import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
    selector: 'jhi-confirm-dialog',
    templateUrl: './confirm-dialog.component.html'
})
export class ConfirmDialogComponent {
    constructor(public dialogRef: MatDialogRef<ConfirmDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

    onNoClick(): void {
        this.dialogRef.close();
    }
}

export interface DialogData {
    confirmed: false;
    title: string;
    text: string;
}
