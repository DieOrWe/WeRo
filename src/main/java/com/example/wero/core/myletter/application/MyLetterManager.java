package com.example.wero.core.myletter.application;

import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.myletter.infrastructure.MyLetterRepository;

import com.example.wero.core.receiveduser.infrastructure.ReceivedUserRepository;
import com.example.wero.core.senduser.application.SendUserEditor;
import com.example.wero.core.senduser.infrastructure.SendUserRepository;
import com.example.wero.core.user.domain.User;
import com.example.wero.core.user.infrastructure.UserRepository;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Service
//@EnableJpaRepositories
public class MyLetterManager implements MyLetterFinder, MyLetterEditor {
    private final MyLetterRepository myLetterRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final SendUserEditor sendUserEditor;
    private final SendUserRepository sendUserRepository;
    private final ReceivedUserRepository receivedUserRepository;
    
    public MyLetterManager(MyLetterRepository myLetterRepository, ModelMapper modelMapper, UserRepository userRepository, SendUserEditor sendUserEditor, SendUserRepository sendUserRepository, ReceivedUserRepository receivedUserRepository) {
        this.myLetterRepository = myLetterRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.sendUserEditor = sendUserEditor;
        this.sendUserRepository = sendUserRepository;
        this.receivedUserRepository = receivedUserRepository;
    }
    
    @Override
    public MyLetterDTO findMyLetter(String myLetterId) { // 쓰일 일이 있을 것 같아서 일단 구현
        String message = String.format("%s에 해당하는 MyLetter 가 없습니다.", myLetterId);
        final MyLetter myLetter = myLetterRepository.findById(myLetterId).orElseThrow(() -> new NoSuchElementException(message));
        return modelMapper.map(myLetter, MyLetterDTO.class);
    }

    @Override
    public String createMyLetter(MyLetterDTO newMyLetterDTO) {
        if (myLetterRepository.findById(newMyLetterDTO.getMyLetterId()).isPresent()) {
            String message = String.format("다시 시도해주세요. : %s", newMyLetterDTO.getMyLetterTitle());
            System.out.println("이미 존재하는 MyLetterId: " + newMyLetterDTO.getMyLetterId() + "입니다");
            throw new IllegalArgumentException(message);
        }
        String message = String.format("존재하지 않는 사용자 ID: %s", newMyLetterDTO.getWriterId());
        User user = userRepository.findById(newMyLetterDTO.getWriterId()).orElseThrow(() -> new NoSuchElementException(message));
        System.out.println("--------------User: " + user);

        BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
        String letterId = scpwd.encode(newMyLetterDTO.getMyLetterId());

        MyLetter myLetter = modelMapper.map(newMyLetterDTO, MyLetter.class);
        System.out.println("------------------------- myLetter: " + myLetter);
        myLetter.setUser(user);
        myLetter.setMyLetterId(letterId);
        myLetterRepository.save(myLetter);
        return sendUserEditor.createUserLetter(myLetter);
    }

    @Override
    public String deleteMyLetter() {
        // ToDo: 받은사람 + 보낸사람 DB에 편지 id 없으면 MyLetter 삭제 (주기적으로 삭제를 하는 기간을 정해서?)
        List<String> sendLetterIds = sendUserRepository.findLetterIds();
        List<String> receivedLetterIds = receivedUserRepository.findLetterIds();
        Iterable<String> joinedIterable = Iterables.unmodifiableIterable(
                Iterables.concat(sendLetterIds, receivedLetterIds)); // sendLetterIds 리스트와 receivedLetterIds 리스트 합치기
        Collection<String> joinedCollection = Lists.newArrayList(joinedIterable); // 합친걸 Collection<String> 으로 변환
        List<String> myLetterIds = myLetterRepository.findLetterIds();
        myLetterIds.removeAll(joinedCollection); // myLetterIds랑 중복되는 데이터(받은사람+보낸사람이 존재하는 myLetter)들은 제거하고
                                                 // myLetterIds 에만 존재하는 데이터만 남겨져있음.
        for (String myLetterId:myLetterIds) {
            myLetterRepository.deleteById(myLetterId);
            System.out.println("myLetterId : " + myLetterId + " 삭제 완료!!");
        }
        String message = String.format("deleteMyLetter() 메소드에 의해 SendUser와 ReceivedUser에 존재 하지 않는 %d 개의 편지 삭제 완료",myLetterIds.size());
        return message;
    }
}
