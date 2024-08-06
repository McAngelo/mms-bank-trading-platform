import { Component, ViewChild } from '@angular/core';
import { ModalConfig, ModalComponent } from '../../../../_metronic/partials';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrl: './wallet.component.scss'
})
export class WalletComponent {
  modalConfig: ModalConfig = {
    modalTitle: 'Fund Wallet',
    //dismissButtonLabel: 'Submit',
    //closeButtonLabel: 'Cancel'
  };
  @ViewChild('modal') private modalComponent: ModalComponent;
  constructor() {}

  async openModal() {
    return await this.modalComponent.open();
  }
}
