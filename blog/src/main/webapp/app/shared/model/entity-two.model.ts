export interface IEntityTwo {
  id?: number;
  name?: string;
  email?: string;
  phone?: string;
}

export class EntityTwo implements IEntityTwo {
  constructor(public id?: number, public name?: string, public email?: string, public phone?: string) {}
}
