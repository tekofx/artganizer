export default interface Submission {
    id: number;
    title: string;
    description: string;
    date: Date;
    rating: number;
    width: number;
    height: number;
    artist: string;
    folders: number[];
    labels: number[];
    characters: number[];
    format: string;
    image: string;
}