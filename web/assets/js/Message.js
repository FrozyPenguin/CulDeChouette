export class Message {
    constructor(message, type = 'SIMPLEMESSAGE') {
        if(!message) throw 'Message invalide';
        this.message = {
            type,
            message
        };
    }
    
    toString() {
        console.log(this.message)
        return JSON.stringify(this.message);
    }
}

