import { useState, useEffect, useContext } from "react";
import { motion } from "framer-motion";
import { Grid, Typography, Paper, Stack, Dialog, Accordion, AccordionSummary, AccordionDetails } from "@mui/material";
import { Skeleton } from "@mui/material";
import axios from "axios";
import Image from "../interfaces/Image";
import Submission from "../interfaces/Submission";
import { DataContext } from "../pages/_app";


type GalleryProps = {
    submissions: Submission[]
}
export default function Gallery() {
    const { data, setData } = useContext(DataContext);
    const [submissions, setSubmissions] = useState<Submission[]>(data.submissions);

    const [value, setValue] = useState<Image>();
    const [open, setOpen] = useState(false);

    const toggleOpen = () => {
        setOpen(!open);
    };

    const onImageClick = (image: Image) => {
        setValue(image);
        setOpen(true);
    };


    useEffect(() => {
        console.log("Gallery useEffect")
        var temp = data.submissions;
        if (data.filters.rating > -1) {
            console.log(`Filtering submissions with rating ${data.filters.rating}`)
            temp = submissions.filter(submission => submission.rating >= data.filters.rating)
            console.log(`Found ${submissions.length} submissions`)
        }

        if (data.filters.tags.length > 0) {
            console.log("Gallery useEffect tags ")
            console.log(data.filters.tags)
            console.log("Gallery useEffect tags ")
            console.log(submissions)
            temp = submissions.filter(submission => submission.tags?.some(tag => data.filters.tags.some(filterTag => filterTag.id === tag.id)));

        }

        if (data.filters.folders.length > 0) {
            console.log("Gallery useEffect folders ")
            console.log(data.filters.folders)
            console.log("Gallery useEffect folders ")
            console.log(submissions)
            temp = submissions.filter(submission => submission.folders?.some(folder => data.filters.folders.some(filterFolder => filterFolder.id === folder.id)));
        }

        if (temp !== submissions) {

            setSubmissions(temp);
            console.log("Setting submissions")
        }
    }, [data.filters.rating, data.filters.tags, data.filters.folders])



    function ImageList() {
        return (
            <div>
                {submissions.map((image) => (
                    <motion.div className="pics"
                        whileHover={{ scale: 1.05 }}
                        transition={{ type: "spring", stiffness: 400, damping: 20 }}
                        onClick={() => onImageClick(image)}
                        key={image.id}
                    >
                        <Stack direction="column" spacing={1} justifyContent="center" alignItems="center">

                            <img src={image.image} className="pic" />
                            <Typography variant="image_title">{image.title}</Typography>
                            <Typography>{image.width}x{image.height}</Typography>

                        </Stack>

                    </motion.div>
                ))}
            </div>
        )
    }


    return (
        <Paper sx={{ minHeight: '100vh' }}>
            <div className="gallery">
                {data.submissions.length == 0 && <Stack direction="column" spacing={1} justifyContent="center" alignItems="center">

                    <Typography variant="h1">No submissions yet</Typography>
                </Stack>}
                <ImageList />
            </div>


            <Dialog open={open} onClose={toggleOpen} maxWidth="lg" PaperProps={{
                style: {
                    backgroundColor: 'transparent',
                    backgroundImage: "unset",
                    boxShadow: 'none',
                },
            }}>
                <img src={value?.image} style={{ height: "100%", width: "100%", objectFit: "contain" }} onClick={toggleOpen} />
            </Dialog>
        </Paper >

    );
}