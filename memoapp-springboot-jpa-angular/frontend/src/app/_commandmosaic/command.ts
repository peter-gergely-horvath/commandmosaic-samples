export class Command<T> {

    protocol = 'CM/1.0';

    constructor(
        public command: string,
        public parameters?: {},
        public auth?: {
            user?: string;
            password?: string;
            token?: string;
        },
        ) {}
}
