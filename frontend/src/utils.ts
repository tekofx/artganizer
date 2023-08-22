import Artist from "../interfaces/Artist";
import Character from "../interfaces/Character";
import Settings from "../interfaces/Settings";
import Submission from "../interfaces/Submission";
const emptyArtist: Artist = {
  id: 0,
  name: "",
  description: "",
  image: "/placeholder.jpg",
  submissions: [],
  socials: [],
};
const emptyCharacter: Character = {
  id: 0,
  name: "",
  description: "",
  image: "/placeholder.jpg",
};

const emptySubmission: Submission = {
  id: 0,
  title: "",
  description: "",
  image: "/placeholder.jpg",
  artist: emptyArtist,
  date: new Date(),
  rating: 0,
  tags: [],
  characters: [],
  colors: [],
  folders: [],
  format: "",
  height: 0,
  width: 0,
  size: 0,
};

const defaultSettings: Settings = {
  galleryInfo: {
    title: true,
    tags: false,
    rating: false,
    characters: false,
    artist: false,
    dimensions: true,
    date: false,
    colors: false,
  },
};
export { emptyArtist, emptySubmission, defaultSettings, emptyCharacter };
