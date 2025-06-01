package dev.tekofx.artganizer.repository

import dev.tekofx.artganizer.dao.ITagDao
import dev.tekofx.artganizer.entities.Tag

class TagRepository(private val tagDao: ITagDao) {

    // INSERT
    suspend fun insertTag(tag: Tag) = tagDao.insert(tag)

    // GET
    fun getAllTags() =
        tagDao.getTags()

    suspend fun getTagById(tagId: Long) =
        tagDao.getTagById(tagId)

    // UPDATE
    suspend fun updateTag(tag: Tag) = tagDao.update(tag)

    // DELETE
    suspend fun deleteTag(tag: Tag) = tagDao.delete(tag)


    // OTHER
    fun doesTagExist(tagName: String): Boolean {
        return tagDao.doesTagExist(tagName)
    }

}