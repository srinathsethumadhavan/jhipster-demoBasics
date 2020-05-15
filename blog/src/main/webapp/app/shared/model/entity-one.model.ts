export interface IEntityOne {
  id?: number;
  name?: string;
}

export class EntityOne implements IEntityOne {
  constructor(public id?: number, public name?: string) {}
}
