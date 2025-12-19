export interface Rental {
	id: number,
	name: string,
	surface: number,
	price: number,
	picture: string,
	description: string,
	ownerId: number,
	createdAt: Date,
	updatedAt: Date
}