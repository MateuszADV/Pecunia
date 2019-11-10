package pl.mateusz.Pecunia.services.OrderService;

import org.springframework.stereotype.Service;
import pl.mateusz.Pecunia.models.dtos.NoteInfoViewDto;
import pl.mateusz.Pecunia.models.dtos.OrderItemDto;

@Service
public interface OrderService {
    OrderItemDto orderItemDto(NoteInfoViewDto noteInfoViewDto);
}
