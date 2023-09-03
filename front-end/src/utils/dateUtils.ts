export class DateUtils {

    private static getMonth(month: string) {
        switch (month) {
            case 'Jan':
                return '01';
            case 'Feb':
                return '02';
            case 'Mar':
                return '03';
            case 'Apr':
                return '04';
            case 'May':
                return '05';
            case 'Jun':
                return '06';
            case 'Jul':
                return '07';
            case 'Aug':
                return '08';
            case 'Sep':
                return '09';
            case 'Oct':
                return '10';
            case 'Nov':
                return '11';
            case 'Dec':
                return '12';
        }
    }

    static toString(date: Date) {
        if (date === null || date === undefined) return "";
        const elements = date.toString().split(' ');
        const month = this.getMonth(elements[1]);
        const time = elements[4].split(':');
        return `${elements[2]}/${month}/${elements[3]} ${time[0]}:${time[1]}`
    }

    static toJson(date: Date) {
        let string = date.toISOString();
        let elements = string.split('T');
        return `${elements[0]} ${elements[1].split('.')[0]}`;
    }
}