import {
  Artist,
  Character,
  Filters,
  Settings,
  Submission,
} from "../interfaces";
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
  format: "",
  height: 0,
  width: 0,
  size: 0,
  filename: "",
  original_image: "",
  thumbnail: "",
};

const defaultSettings: Settings = {
  galleryInfo: {
    title: true,
    tags: false,
    rating: false,
    artist: false,
    dimensions: true,
    date: false,
    colors: false,
  },
};

const emptyFilters: Filters = {
  rating: -1,
  tags: [],
  artists: [],
  title: "",
  characters: [],
  color: "",
};

export {
  defaultSettings,
  emptyArtist,
  emptyCharacter,
  emptyFilters,
  emptySubmission,
};
