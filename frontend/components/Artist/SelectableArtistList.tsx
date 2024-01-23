import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { Typography, MenuItem } from "@mui/material";
import Artist from "../../interfaces/Artist";
import ArtistLabel from "./ArtistLabel";
import axios from "axios";
interface ArtistListProps {
  selectedArtist: Artist | undefined;
  setSelectedArtist: Dispatch<SetStateAction<Artist | undefined>>;
  setOpen: Dispatch<SetStateAction<boolean>>;
  filter: string;
}

export default function SelectableArtistList(props: ArtistListProps) {
  const [artists, setArtists] = useState<Artist[]>([]);
  useEffect(() => {
    const getArtists = async () => {
      var res = await axios.get(process.env.API_URL + "/artists");
      setArtists(res.data);
    }
    getArtists();
  }
    , []);

  function handleClick(artist: Artist) {
    props.setSelectedArtist(artist);
    props.setOpen(false);
  }

  return (
    <>
      {artists.filter((artist) =>
        artist.name.toLowerCase().includes(props.filter.toLowerCase())
      ).length == 0 && <Typography>No artists</Typography>}
      {artists

        .filter((artist) =>
          artist.name.toLowerCase().includes(props.filter.toLowerCase())
        )

        .map((artist) => (
          <MenuItem
            key={artist.id}
            selected={props.selectedArtist?.id == artist.id}
            onClick={() => handleClick(artist)}
          >
            <ArtistLabel artist={artist} />
          </MenuItem>
        ))}
    </>
  );
}
