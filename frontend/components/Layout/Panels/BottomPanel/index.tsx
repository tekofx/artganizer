import { Paper, Stack } from "@mui/material";
import axios from "axios";
import { useRouter } from "next/router";
import React, { useEffect, useRef } from "react";
import { Submission } from "../../../../interfaces";
interface BottomPanelProps {
  current: Submission;
}
export default function BottomPanel({ current }: BottomPanelProps) {
  const router = useRouter();
  const ref = useRef<HTMLDivElement>(null);
  const [submissions, setSubmissions] = React.useState<Submission[]>([]);

  useEffect(() => {
    const getSubmissions = async () => {
      var res = await axios.get("/api/submissions");
      setSubmissions(res.data);
    };
    getSubmissions();
  }, []);

  const handleWheel = (e: React.WheelEvent) => {
    if (ref.current) {
      ref.current.scrollLeft += e.deltaY;
    }
  };
  return (
    <Paper
      sx={{ overflowX: "auto", maxHeight: "20vh", height: "100%" }}
      onWheel={handleWheel}
      ref={ref}
    >
      <Stack direction="row" sx={{ width: "max-content" }}>
        {submissions.map((submission) => (
          <img
            key={submission.id}
            src={submission.thumbnail}
            height="150px"
            style={{
              border: submission.id == current.id ? "10px solid #555" : "none",
              cursor: "pointer",
            }}
            onClick={() => router.push(`/submission/${submission.id}`)}
          />
        ))}
      </Stack>
    </Paper>
  );
}
