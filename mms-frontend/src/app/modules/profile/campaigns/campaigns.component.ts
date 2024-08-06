import { Component } from '@angular/core';
import { IconUserModel } from '../../../_metronic/partials';
@Component({
  selector: 'app-campaigns',
  templateUrl: './campaigns.component.html',
})
export class CampaignsComponent {
  stocks: Array<IconUserModel> = [
    { name: 'Emma Smith', avatar: './assets/media/svg/brand-logos/microsoft-5.svg' },
    { name: 'Rudy Stone', avatar: './assets/media/svg/brand-logos/amazon.svg' }
  ];

  stocks2: Array<IconUserModel> = [
    { name: 'Rudy Stone', avatar: './assets/media/svg/brand-logos/oracle.svg' },
    { name: 'Emma Smith', avatar: './assets/media/svg/brand-logos/apple-black.svg' },
    { name: 'ibm', avatar: './assets/media/svg/brand-logos/ibm-logo.svg' },
  ];
  constructor() {}
}
