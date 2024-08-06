import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'appliedBy'
})
export class ApplicationModePipe implements PipeTransform {
    transform(value: string, param: string) {
        if (!value) return 'N/A';

        try{
            let mode;
            if(value == "Myself on the website"){
                mode ={
                    id:  "00000000",
                    name: `Self application on the website by`,
                    phoneNumber: "",
                    role: "Applicant"
                  }
            }else{
                mode = JSON.parse(value);
            }
            
            return mode[param];
        } catch (error) {
            console.error("Error parsing JSON:", error);
            return 'N/A';
        }
    }
}