package com.example.week3.Contrloller;

import com.example.week3.Dto.RequestDto.PostRequestDto;
import com.example.week3.Dto.ResponseDto.CommentResponseDto;
import com.example.week3.Dto.ResponseDto.PostResponseDto;
import com.example.week3.Entity.Comment;
import com.example.week3.Entity.Post;
import com.example.week3.Repository.CommentRepository;
import com.example.week3.Repository.PostRepository;
import com.example.week3.Service.PostService;
import com.example.week3.Service.UserService;
import com.example.week3.util.ErrorList;
import com.example.week3.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final UserService userService;
    private final CommentRepository commentRepository;

    //게시글 등록
    @PostMapping("/auth/posts")
    public Response createPost(@RequestBody PostRequestDto requestDto) {
        String nickName=userService.NickName();
        Post post = new Post(requestDto,nickName);
        postRepository.save(post);
        PostResponseDto responseDto = new PostResponseDto(post);
        Response response = new Response(true,responseDto,null);
        return response;
    }

    // 게시글 조회
    @GetMapping("/api/posts")
    public Response getPosts(){
        List<Post> temp_list = postRepository.findAll();
        ArrayList<PostResponseDto> response_list = new ArrayList<>();
        for (int i = 0; i < temp_list.size(); i++) {
            Post temp_response = temp_list.get(i);
            PostResponseDto responseDto = new PostResponseDto(temp_response);
            response_list.add(responseDto);
        }

        Response response = new Response(true,response_list,null);
        return response;
    }



    // 게시글 상세 조회
    @GetMapping("/api/posts/{id}")
    public Response thisPosts(@PathVariable Long id){
        Long dbsize=postRepository.count();
        ErrorList errorList = new ErrorList();
        Response response;
        if (id<=dbsize) {
            List<Comment> temp_list = commentRepository.findAllByPostId(id);
            ArrayList<CommentResponseDto> response_list = new ArrayList<>();

            for (int i = 0; i < temp_list.size(); i++) {
                Comment temp_response=temp_list.get(i);
                CommentResponseDto responseDto = new CommentResponseDto(temp_response);
                response_list.add(responseDto);
            }

            //출력에 postRepository.findById(id)추가
            response = new Response(true, response_list, null);
        }
        else{
            errorList.none_ID();
            response = new Response(false, postRepository.findById(id), errorList);
        }
        return response;
    }



    // 게시글 수정
    @PutMapping("/auth/posts/{id}")
    public Response editPost(@PathVariable Long id,@RequestBody PostRequestDto requestDto){
        Response response = null;
        Long dbsize=postRepository.count();
        ErrorList errorList = new ErrorList();
        String nickName=userService.NickName();

        if(id<=dbsize){
            if(postRepository.existsByNameAndId(nickName,id)) {
                postService.edit(id, requestDto);
                response = new Response(true, postRepository.findById(id), null);
            }
            else {
                errorList.mis_name();
                response = new Response(false, null, errorList);
            }
        }
        else {
            errorList.none_ID();
            response = new Response(false, null, errorList);
        }
        return response;
    }

    // 게시글 삭제
    @DeleteMapping("/auth/post/{id}")
    public Response delPost(@PathVariable Long id){
        Response response;
        Long dbsize=postRepository.count();
        ErrorList errorList = new ErrorList();
        String nickName=userService.NickName();

        if(id<=dbsize) {
            if(postRepository.existsByNameAndId(nickName,id)) {
                postRepository.deleteById(id);
                response = new Response(true, true, null);
            }
            else {
                errorList.mis_name();
                response = new Response(false, null, errorList);
            }
        }
        else {
            errorList.none_ID();
            response = new Response(false, null, errorList);
        }
        return response;
    }

}