import { TestBed } from '@angular/core/testing';

import { UserDataStoreService } from './user-data-store.service';

describe('UserDataStoreService', () => {
  let service: UserDataStoreService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserDataStoreService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
