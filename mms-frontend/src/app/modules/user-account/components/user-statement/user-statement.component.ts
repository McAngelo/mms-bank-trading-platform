import { Component, OnInit  } from '@angular/core';


interface AccountStatement {
  date: Date;
  transaction: string;
  currency: string;
  deposit: number | null;
  withdrawal: number | null;
  closingBalance: number;
}

@Component({
  selector: 'app-user-statement',
  templateUrl: './user-statement.component.html',
  styleUrl: './user-statement.component.scss'
})
export class UserStatementComponent implements OnInit{

  accountStatements: AccountStatement[] = []


  constructor(){}

  ngOnInit(): void {
    this.generateAccountStatements(10);
    console.log(this.accountStatements)
  }


 getRandomDate(start: Date, end: Date): Date {
    const startTime = start.getTime();
    const endTime = end.getTime();
    return new Date(startTime + Math.random() * (endTime - startTime));
}

getRandomValue(min: number, max: number): number {
    return parseFloat((Math.random() * (max - min) + min).toFixed(2));
}

 getRandomTransactionType(): string {
    const transactionTypes = ['Credit', 'Debit', 'Transfer', 'Payment'];
    return transactionTypes[Math.floor(Math.random() * transactionTypes.length)];
}

 generateAccountStatements(num: number): AccountStatement[] {
    const accountStatements: AccountStatement[] = [];
    const startDate = new Date('2024-05-01');
    const endDate = new Date();
    let closingBalance = this.getRandomValue(100, 10000); // Initial closing balance

    for (let i = 0; i < num; i++) {
        const date = this.getRandomDate(startDate, endDate);
        const transaction = this.getRandomTransactionType();
        const currency = "USD";
        const isDeposit = Math.random() > 0.5;

        let deposit = null;
        let withdrawal = null;

        if (isDeposit) {
            deposit = this.getRandomValue(10, 5000);
            closingBalance += deposit;
        } else {
            withdrawal = this.getRandomValue(10, 5000);
            closingBalance -= withdrawal;
        }

        closingBalance = parseFloat(closingBalance.toFixed(2));

        this.accountStatements.push({
            date,
            transaction,
            currency,
            deposit,
            withdrawal,
            closingBalance
        });
    }

    return accountStatements;
}

}
