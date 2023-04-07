package com.realtime.blog_api.controllers

import com.realtime.blog_api.dto.PostDto
import com.realtime.blog_api.constants.AppConstants
import com.realtime.blog_api.payloads.ApiResponse
import com.realtime.blog_api.payloads.PostResponse
import com.realtime.blog_api.services.PostService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class PostController(@Autowired private val postService: PostService) {

    // POST -> create post
    @PostMapping("/user/{userId}/category/{categoryId}/post")
    fun createPost(@RequestBody postDto : PostDto, @PathVariable userId: Int, @PathVariable categoryId: Int) : ResponseEntity<PostDto> = ResponseEntity<PostDto>(this.postService.createPost(postDto, userId, categoryId), HttpStatus.CREATED)

    // PUT -> update post
    @PutMapping("/post/{postId}")
    fun updatePost(@RequestBody postDto: PostDto, @PathVariable("postId") postId:Int) : ResponseEntity<PostDto> = ResponseEntity.ok(this.postService.updatePost(postDto, postId))

    // DELETE -> delete post
    @DeleteMapping("/post/{postId}")
    fun deletePost(@PathVariable postId:Int) : ResponseEntity<ApiResponse> {
        this.postService.deletePost(postId)
        return ResponseEntity<ApiResponse>(ApiResponse("post is deleted successfully!!", true), HttpStatus.OK)
    }

    //  GET -> get post
    @GetMapping("/post/{postId}")
    fun getPost(@PathVariable("postId") postId:Int) : ResponseEntity<PostDto> = ResponseEntity<PostDto>(this.postService.getPostById(postId), HttpStatus.OK)

    // GET -> get all post
    @GetMapping("/post/all")
    fun getAllPosts(
        @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) pageNumber: Int,
        @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) pageSize: Int,
        @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) sortBy: String,
        @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) sortDir: String,
        ) : ResponseEntity<PostResponse> = ResponseEntity<PostResponse>(this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK)

    // get post by user id
    @GetMapping("/user/{userId}/post")
    fun getPostByUser(@PathVariable userId: Int): ResponseEntity<List<PostDto>> = ResponseEntity<List<PostDto>>(this.postService.getPostByUser(userId), HttpStatus.OK)

    // get post by category id
    @GetMapping("/category/{categoryId}/post")
    fun getPostByCategory(@PathVariable categoryId: Int): ResponseEntity<List<PostDto>> = ResponseEntity<List<PostDto>>(this.postService.getPostByCategory(categoryId), HttpStatus.OK)

    @GetMapping("/post/search/{keyword}")
    fun searchByKeyword(@PathVariable keyword: String): ResponseEntity<List<PostDto>> = ResponseEntity<List<PostDto>>(this.postService.searchPosts(keyword), HttpStatus.OK)

}