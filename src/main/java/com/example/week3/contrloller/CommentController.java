package com.example.week3.Contrloller;

import com.example.week3.Dto.CommentDto;
import com.example.week3.Dto.RequestDto.CommentRequestDto;
import com.example.week3.Dto.RequestDto.PostRequestDto;
import com.example.week3.Dto.ResponseDto.CommentResponseDto;
import com.example.week3.Entity.Comment;
import com.example.week3.Repository.CommentRepository;
import com.example.week3.Service.CommentService;
import com.example.week3.Service.UserService;
import com.example.week3.util.ErrorList;
import com.example.week3.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentRepository commentRepository;

    private final CommentService commentService;

    private final UserService userService;

    // 댓글 작성
    @PostMapping("/auth/comment")
    public Response createComment(@RequestBody CommentDto commentDto){
        // Long id=user.getUserId();
        String nickName=userService.NickName();
        Comment comment = new Comment(commentDto, nickName);
        System.out.println(nickName+"!");
        commentRepository.save(comment);
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        Response response = new Response(true,responseDto,null);
        return response;
    }

    //댓글 목록 조회
    @GetMapping("/api/comment/{id}")
    public Response getComments(@PathVariable Long id){
        List<Comment> temp_list = commentRepository.findAllByPostId(id);
        ArrayList<CommentResponseDto> response_list = new ArrayList<>();

        for (int i = 0; i < temp_list.size(); i++) {
            Comment temp_response=temp_list.get(i);
            CommentResponseDto responseDto = new CommentResponseDto(temp_response);
            response_list.add(responseDto);
        }

        Response response = new Response(true,response_list,null);
        return response;
    }

    // 댓글 수정
    @PutMapping("/auth/comment/{id}")
    public Response editComment(@PathVariable Long postid,@PathVariable Long id,@RequestBody CommentRequestDto requestDto){
        Response response;
        Long dbsize=commentRepository.count();
        ErrorList errorList = new ErrorList();
        String nickName=userService.NickName();

        if(id<=dbsize){
            if(commentRepository.existsByAuthorAndId(nickName,id)) {
                commentService.edit(id, requestDto);
                response = new Response(true, commentRepository.findById(id), null);
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

    //댓글 삭제
    @DeleteMapping("/auth/comment/{id}")
    public Response delPost(@PathVariable Long id){
        Response response;
        Long dbsize=commentRepository.count();
        ErrorList errorList = new ErrorList();
        String nickName=userService.NickName();

        if(id<=dbsize) {
            if(commentRepository.existsByAuthorAndId(nickName,id)) {
                commentRepository.deleteById(id);
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