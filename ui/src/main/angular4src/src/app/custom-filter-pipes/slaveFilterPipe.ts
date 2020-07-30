import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
    name: "slaveLabelfilter",
    pure: false
})
// this class is used for filtering and creating list of labels for all the slaves defined in the application
export class SlaveLabelfilterPipe implements PipeTransform {
    readonly onStr = "on";
    transform(slaveDetails: any[], checkValue: string): any {
        // console.log(slaveDetails)
        if (!slaveDetails || !checkValue) {
            return slaveDetails;
        }
        // console.log(checkValue in slaveDetails);
        let keyFlag = false;
        slaveDetails.forEach(slave => {
            if (checkValue.indexOf("build") !== -1 ) {
                const dep = "deploy";
                if ((slave.labels !== undefined) && (slave[checkValue] !== undefined || slave[checkValue] !== undefined)) {
                    keyFlag = true;
                }
            } else {
                if ((slave.labels !== undefined) && (slave[checkValue] !== undefined)) {
                    keyFlag = true;
                }
            }
        });
        // console.log(keyFlag)
        if (checkValue.indexOf("build") !== -1 && keyFlag ) {
            // console.log(checkValue)
            const dep = "deploy";
            const labelList = [];
            slaveDetails.forEach(slave => {
                if ( ((slave[checkValue] !== undefined) && (slave[checkValue].indexOf(this.onStr) !== -1)) ||
                        ((slave[dep] !== undefined) && (slave[dep].indexOf(this.onStr) !== -1)) ) {
                    const sLabels = slave.labels.split(" ");
                    sLabels.forEach( label => {
                        if (labelList.indexOf(label) === -1 && label !== "" ) {
                            labelList.push(label);
                        }
                    });
                }
            });
            return labelList;
            // return
            // slaveDetails.filter(slave => (slave[checkValue].indexOf(this.onStr) !== -1 || slave[dep].indexOf(this.onStr) !== -1 ) )
            //                     .map(slave => slave.labels);
        } else if (checkValue.indexOf("test") !== -1 && keyFlag) {
            // console.log(checkValue)
            const labelList = [];
            slaveDetails.forEach(slave => {
                if ( (slave[checkValue] !== undefined) &&
                    (slave[checkValue].indexOf(this.onStr) !== -1) ) {
                    const sLabels = slave.labels.split(" ");
                    sLabels.forEach( label => {
                        if (labelList.indexOf(label) === -1 && label !== "" ) {
                            labelList.push(label);
                        }
                    });
                }
            });
            return labelList;
            // return slaveDetails.filter(slave => slave[checkValue].indexOf(this.onStr) !== -1)
            //                     .map(slave => slave.labels);
        } else {
            // console.log("ParameterInput JSON doesnot contain Slave usage keys");
            return slaveDetails;
        }
        // filters slaveDetails array, slaves which match and return true will be
        // kept, false will be filtered out
    }
}
